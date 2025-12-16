package com.mosta3d.fawrybook.auth.composable

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mosta3d.fawrybook.R
import com.mosta3d.fawrybook.auth.event.SignUpEvent
import com.mosta3d.fawrybook.auth.viewmodel.SignUpViewModel
import com.mosta3d.fawrybook.shared.composable.AppTextField
import com.mosta3d.fawrybook.shared.composable.AppSecretField

@Preview
@Composable
fun SignupScreen(
    navController: NavController = NavController(LocalContext.current)
) {
    val signUpViewModel = hiltViewModel<SignUpViewModel>(LocalContext.current as ComponentActivity)
    val state by signUpViewModel.state.collectAsStateWithLifecycle()

    val localContext = LocalContext.current



    LaunchedEffect(Unit) {
        signUpViewModel.signUpEventFlow.collect {
            when (it) {
                is SignUpEvent.Success -> {
                    navController.navigate("add-partner")
                }

                is SignUpEvent.Error -> {
                    Toast.makeText(
                        localContext,
                        it.messages[0],
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

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
                name = "username",
                labelId = R.string.username,
                fieldData = state.usernameFieldData,
                onChange = {
                    signUpViewModel.onUsernameChange(it)
                },
            )

            AppTextField(
                name = "email",
                labelId = R.string.email,
                fieldData = state.emailFieldData,
                onChange = {
                    signUpViewModel.onEmailChange(it)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            AppSecretField(
                name = "password",
                labelId = R.string.password,
                fieldData = state.passwordFieldData,
                onChange = {
                    signUpViewModel.onPasswordChange(it)
                }
            )

            AppSecretField(
                name = "confirm-password",
                labelId = R.string.confirm_password,
                fieldData = state.confirmPasswordFieldData,
                onChange = {
                    signUpViewModel.onConfirmPasswordChange(it)
                }
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraSmall,
                onClick = {
                    signUpViewModel.submit()
                }) {
                Text(text = stringResource(R.string.continue_))
            }
        }
    }
}