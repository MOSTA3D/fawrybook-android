package com.mosta3d.fawrybook.auth.composables

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mosta3d.fawrybook.R
import com.mosta3d.fawrybook.auth.event.LoginEvent
import com.mosta3d.fawrybook.auth.viewmodel.LoginViewModel
import com.mosta3d.fawrybook.shared.state.AppState

@Preview
@Composable
fun LoginScreen(
    navController: NavController = NavController(LocalContext.current)
) {
    val loginViewModel = hiltViewModel<LoginViewModel>(LocalContext.current as ComponentActivity)
    val state by loginViewModel.state.collectAsStateWithLifecycle()

    val localContext = LocalContext.current

    LaunchedEffect(Unit) {
        loginViewModel.loginEventFlow.collect {
            when (it) {
                is LoginEvent.Success -> AppState.authenticate(
                    userId = it.userId,
                    email = it.email,
                    token = it.token
                )

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
            // todo custom input elements -> use composable-s wrappers to have the
            //  text-field + error message text + icon and other common logic
            Box {
                OutlinedTextField(
                    label = { Text(text = "Username") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            state.emailField.touched = true
                            loginViewModel.onEmailChange(state.emailField.value)
                        },
                    placeholder = { Text(text = "Username") },
                    value = state.emailField.value,
                    onValueChange = {
                        loginViewModel.onEmailChange(it)
                    }
                )

                if (state.emailField.touched && !state.emailField.isValid())
                    Text(color = MaterialTheme.colorScheme.error, text = "Email has error")
            }

            Box {
                OutlinedTextField(
                    label = { Text(text = "Password") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Password") },
                    value = state.passwordField.value,
                    visualTransformation = {
                        TransformedText(
                            if (state.isPasswordVisibleField.value) it
                            else AnnotatedString("*".repeat(it.length)),
                            OffsetMapping.Identity
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier.clickable { loginViewModel.togglePasswordVisibility() }
                        )
                    },
                    onValueChange = {
                        loginViewModel.onPasswordChange(it)
                    }
                )

                if (!state.passwordField.isValid()) Text(
                    color = MaterialTheme.colorScheme.error,
                    text = "Password has error"
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraSmall,
                onClick = {
                    loginViewModel.login()
                }) {
                Text(text = "Login")
            }
        }
    }
}