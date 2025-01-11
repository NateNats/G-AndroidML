package com.N2Project.androidml

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Vertices
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.navigation.NavController
import com.N2Project.androidml.ui.theme.AndroidMLTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun CatNDog(modifier : Modifier = Modifier) {
//fun CatNDog() {

    var selectedImg by remember { mutableStateOf<Uri?>(null) }
    var message by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,


    ) {
        Column (
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Placeholder input gambar
            Box(
                modifier = Modifier
                    .size(600.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // tombol untuk memilih gambar atau mengambil gambar
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),

            ) {

                // buat milih gambar dari folder
                ImageHandler.ImagePicker { uri ->
                    selectedImg = uri

                    ImageHandler.isCancel = true
                    ImageHandler.isClassified = true

                    message = selectedImg.toString()
                }

                // buat ambil gambar dari kamera
//                ImageHandler.CameraPicker { uri ->
//                    selectedImg = uri
//
//                    ImageHandler.isCancel = true
//                    ImageHandler.isClassified = true
//
//                    message = selectedImg.toString()
//                }
            }

            // tombol untuk lanjut proses atau tidak

            Spacer(modifier = Modifier.height(64.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                ImageHandler.CancelProcess {
                    message = "Halo, tombol ini CancelProcess() berhasil di click, tapi belum berfungsi"
                    ImageHandler.isCancel = false
                    ImageHandler.isClassified = false
                    message = ""
                }
                ImageHandler.ClassifyProcess {
                    message = "Halo, tombol ini ClassifyProcess() berhasil di click, tapi belum berfungsi"
                }
            }
        }
    }
}

@Preview(showBackground = true)

@Composable
fun MainScreenPreview() {
    AndroidMLTheme {
        CatNDog()
    }
}