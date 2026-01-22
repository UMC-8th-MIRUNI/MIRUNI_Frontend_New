package com.miruni.feature.home.runSchedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miruni.core.designsystem.MiruniTheme
import com.miruni.core.designsystem.MiruniTypography
import com.miruni.feature.home.R
import com.miruni.feature.home.dnd.component.button.SingleGreenButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackToScreen(
    navController: NavHostController,
) {
    BackToDndSetContent(
        onGoBackClick = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BackToDndSetContent(
    onGoBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(60.dp))

        Image(
            painter = painterResource(id = R.drawable.miruni_basic),
            contentDescription = null,
            modifier = Modifier.size(195.dp)
        )

        Spacer(Modifier.height(60.dp))

        Text(
            text = "앱으로 돌아가\n성장을 이어가보세요!",
            style = MiruniTypography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(80.dp))

        SingleGreenButton(
            onClick = onGoBackClick,
            text = "돌아가기"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BackToScreenPreview() {
    MiruniTheme {
        BackToScreen(
            navController = rememberNavController(),
        )
    }
}