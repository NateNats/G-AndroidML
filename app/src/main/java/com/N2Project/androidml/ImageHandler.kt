package com.N2Project.androidml

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ImageHandler {

    companion object {
        var isClassified: Boolean = false
        var isCancel: Boolean = false

        @Composable
        fun ImagePicker(onClick: () -> Unit) {
            IconButton(
                onClick = {
                    onClick()
                },
                modifier = Modifier.size(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.folder),
                    contentDescription = "Image Picker"
                )
            }
        }

        @Composable
        fun CameraPicker(onClick: () -> Unit) {
            IconButton(
                onClick = {
                    onClick()
                },
                modifier = Modifier.size(100.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "Camera Picker"
                )
            }
        }

        @Composable
        fun CancelProcess(onClick: () -> Unit) {
            Button(
                onClick = {
                    onClick()
                },
                modifier = Modifier.size(150.dp, 70.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                enabled = isCancel
            ) {
                Text(
                    text = "Cancel"
                )
            }
        }

        @Composable
        fun ClassifyProcess(onClick: () -> Unit) {
            Button(
                onClick = {
                    onClick()
                },
                modifier = Modifier.size(150.dp, 70.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                ),
                enabled = isClassified
            ) {
                Text(
                    text = "Classify"
                )
            }
        }
    }
}