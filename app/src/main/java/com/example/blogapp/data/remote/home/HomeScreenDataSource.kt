package com.example.blogapp.data.remote.home

import com.example.blogapp.core.Result
import com.example.blogapp.data.model.Post
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.protobuf.Value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeScreenDataSource {

    @ExperimentalCoroutinesApi
    suspend fun getLatestPosts(): kotlinx.coroutines.flow.Flow<Result<List<Post>>> = callbackFlow {
        val postList = mutableListOf<Post>()

        var postReference: Query? = null

        try {
            postReference = FirebaseFirestore.getInstance().collection("posts")
                .orderBy("created_at", Query.Direction.DESCENDING)
        } catch (e: Throwable) {
            close(e)
        }

        val suscription = postReference?.addSnapshotListener { value, error ->
            if (value == null) return@addSnapshotListener

            try {
                postList.clear()
                for (post in value.documents) {
                    post.toObject(Post::class.java)?.let { fbPost ->
                        fbPost.apply {
                            created_at = post.getTimestamp(
                                "created_at",
                                DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                            )?.toDate()
                        }
                        postList.add(fbPost)
                    }
                }
            } catch (e: Exception) {
                close(e)
            }

            trySend(Result.Success(postList)).isSuccess
        }
        awaitClose { suscription?.remove() }
    }
}

