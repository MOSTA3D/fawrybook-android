package com.mosta3d.fawrybook.auth.composable

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mosta3d.fawrybook.R
import com.mosta3d.fawrybook.auth.event.LoginEvent
import com.mosta3d.fawrybook.auth.viewmodel.LoginViewModel
import com.mosta3d.fawrybook.shared.composable.AppSecretField
import com.mosta3d.fawrybook.shared.composable.AppTextField
import com.mosta3d.fawrybook.shared.state.AppState

@Preview
@Composable
fun LoginScreen(
    navController: NavController = NavController(LocalContext.current)
) {
    val loginViewModel = hiltViewModel<LoginViewModel>(LocalContext.current as ComponentActivity)
    val state by loginViewModel.stateFlow.collectAsStateWithLifecycle()

    val localContext = LocalContext.current

    LaunchedEffect(Unit) {
        loginViewModel.loginEventFlow.collect {
            when (it) {
                is LoginEvent.Success -> {
                    AppState.authenticate(
                        userId = it.userId,
                        email = it.email,
                        token = it.token
                    )
                    navController.navigate("chat")
                }

                is LoginEvent.Error -> Toast.makeText(
                    localContext,
                    it.messages[0],
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Image(
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        painter = painterResource(R.drawable.cinema),
        contentDescription = null
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(integerResource(R.integer.DEFAULT_PADDING).dp)
    ) {
        Column(
            modifier = Modifier.widthIn(250.dp, 500.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            AppTextField(
                name = "email",
                labelId = R.string.email,
                fieldData = state.emailField,
                onChange = {
                    loginViewModel.onEmailChange(it)
                }
            )

            AppSecretField(
                name = "password",
                labelId = R.string.password,
                fieldData = state.passwordField,
                onChange = {
                    loginViewModel.onPasswordChange(it)
                }
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(integerResource(R.integer.DEFAULT_PADDING).dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraSmall,
                    onClick = {
                        loginViewModel.submit()
                    }) {
                    Text(text = "Login")
                }
                Text(
                    text = stringResource(R.string.don_not_have_account),
                    modifier = Modifier.clickable {
                        navController.navigate("signup")
                    }
                )
            }
        }
    }
}