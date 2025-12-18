package com.mosta3d.fawrybook.partner.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosta3d.fawrybook.R
import com.mosta3d.fawrybook.partner.event.AddPartnerEvent
import com.mosta3d.fawrybook.partner.model.AddPartnerResponse
import com.mosta3d.fawrybook.partner.repository.PartnerRepository
import com.mosta3d.fawrybook.partner.state.AddPartnerState
import com.mosta3d.fawrybook.shared.client.AppResponse
import com.mosta3d.fawrybook.shared.form.AppFieldData
import com.mosta3d.fawrybook.shared.form.validator.AppValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AddPartnerViewModel @Inject constructor(
    private val partnerRepository: PartnerRepository
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(AddPartnerState(
        usernameOrEmailField = AppFieldData(
            value = "",
            validators = listOf(
                AppValidator(R.string.username_required) { it.isNotEmpty() },
                AppValidator(R.string.email_invalid) {
                    !it.contains("@") || Patterns.EMAIL_ADDRESS.matcher(
                        it
                    ).matches()
                }
            )
        )
    ))
    val stateFlow = _stateFlow.asStateFlow()

    private val _addPartnerEventFlow = MutableSharedFlow<AddPartnerEvent>()
    val addPartnerEventFlow = _addPartnerEventFlow.asSharedFlow()

    fun onPartnerFieldChange(value: AppFieldData<String>) {
        _stateFlow.value = _stateFlow.value.copy(usernameOrEmailField = value)
    }

    fun addPartner() {
        viewModelScope.launch {
            val response = partnerRepository.addPartner(stateFlow.value.toAddPartnerRequest())
            val event: AddPartnerEvent = mapResponseToEvent(response)
            _addPartnerEventFlow.emit(event)
        }
    }

    private fun mapResponseToEvent(response: AppResponse<AddPartnerResponse?>): AddPartnerEvent {
        return if (response.success) AddPartnerEvent.Success(
            email = response.data?.email ?: "",
            username = response.data?.username ?: "",
            code = response.data?.code
        ) else AddPartnerEvent.Error(response.messages)
    }
}