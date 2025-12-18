package com.mosta3d.fawrybook.partner.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mosta3d.fawrybook.R
import com.mosta3d.fawrybook.partner.event.AddPartnerEvent
import com.mosta3d.fawrybook.partner.viewmodel.AddPartnerViewModel
import com.mosta3d.fawrybook.shared.composable.AppTextField

@Preview
@Composable
fun AddPartnerScreen(navController: NavController = NavController(LocalContext.current)) {
    val viewModel = hiltViewModel<AddPartnerViewModel>()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    val localContext = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.addPartnerEventFlow.collect {
            when (it) {
                is AddPartnerEvent.Success -> {
                    navController.navigate("partner")
                }

                is AddPartnerEvent.Error -> {
                    Toast.makeText(localContext, it.messages[0], Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(integerResource(R.integer.DEFAULT_PADDING).dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(R.integer.DEFAULT_PADDING.dp)
        ) {
            AppTextField(
                name = "username",
                labelId = R.string.username,
                fieldData = state.usernameOrEmailField,
                onChange = {
                    viewModel.onPartnerFieldChange(it)
                }
            )
        }

        Button(
            onClick = {
                viewModel.addPartner()
            }
        ) {
            Text(text = stringResource(R.string.add_partner))
        }
    }
}