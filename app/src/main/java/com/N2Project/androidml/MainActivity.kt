package com.N2Project.androidml

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // Untuk mengatur posisi dan ukuran elemen
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.* // Gunakan Material 3 untuk `Card`, `Button`, dll.
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.N2Project.androidml.ui.theme.AndroidMLTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidMLTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    // State untuk mengatur apakah teks hasil muncul atau tidak
    var showResult by remember { mutableStateOf(false) }

    // Layout utama dengan Constraint-like arrangement menggunakan Box + Alignment
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Image di dalam Card
                Image(
                    painter = painterResource(id = R.drawable.catndog),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    alignment = Alignment.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Button untuk menampilkan hasil
                Button(
                    onClick = { showResult = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Cat and Dog Classifier",
                        fontSize = 20.sp
                    )
                }
            }
        }

        // TextView untuk hasil
        if (showResult) {
            Text(
                text = "Text muncul setelah tombol ditekan!",
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AndroidMLTheme {
        MainScreen()
    }
}
