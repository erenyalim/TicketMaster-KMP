package org.example.ticketmaster.presentation.register

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.animateColorAsState
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import ticketmaster.shared.generated.resources.*


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    viewModel: RegisterViewModel) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            delay(1000)
            viewModel.resetState()
            onNavigateBack()
        }
    }

    val darkBackground = Color(0xFF161618)
    val cyanColor = Color(0xFF00E5FF)
    val darkInputBox = Color(0xFF252525)
    val lightGray = Color(0xFFA0A0A0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (state.currentStep > 1) viewModel.onPreviousStep()
                    else onNavigateBack()
                },
                modifier = Modifier.offset(x = (-12).dp)
            ) {
                Text(
                    text = "←",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomStepper(currentStep = state.currentStep, cyanColor = cyanColor, lightGray = lightGray)
        Spacer(modifier = Modifier.height(40.dp))

        AnimatedContent(
            targetState = state.currentStep,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInHorizontally(animationSpec = tween(400)) { width -> width } + fadeIn(tween(400))) togetherWith
                            (slideOutHorizontally(animationSpec = tween(400)) { width -> -width } + fadeOut(tween(400)))
                } else {
                    (slideInHorizontally(animationSpec = tween(400)) { width -> -width } + fadeIn(tween(400))) togetherWith
                            (slideOutHorizontally(animationSpec = tween(400)) { width -> width } + fadeOut(tween(400)))
                }
            },
            label = "RegisterSteps"
        ) { step ->
            when (step) {
                1 -> PersonalStep(
                    firstName = state.firstName,
                    onFirstNameChange = viewModel::onFirstNameChange,
                    lastName = state.lastName,
                    onLastNameChange = viewModel::onLastNameChange,
                    onNext = { viewModel.onNextStep() },
                    onNavigateBack = onNavigateBack,
                    cyanColor = cyanColor, darkBox = darkInputBox, lightGray = lightGray
                )
                2 -> ContactStep(
                    email = state.email, onEmailChange = viewModel::onEmailChange,
                    phone = state.phone, onPhoneChange = viewModel::onPhoneChange,
                    onNext = { viewModel.onNextStep() },
                    cyanColor = cyanColor, darkBox = darkInputBox, lightGray = lightGray
                )
                3 -> SecurityStep(
                    password = state.password,
                    onPasswordChange = viewModel::onPasswordChange,
                    onRegister = viewModel::register,
                    isLoading = state.isLoading,
                    isSuccess = state.isSuccess,
                    error = state.error,
                    cyanColor = cyanColor, darkBox = darkInputBox, lightGray = lightGray
                )
            }
        }
    }
}


@Composable
fun CustomStepper(currentStep: Int, cyanColor: Color, lightGray: Color) {
    val steps = listOf(
        stringResource(Res.string.register_step_personal),
        stringResource(Res.string.register_step_contact),
        stringResource(Res.string.register_step_security)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        steps.forEachIndexed { index, title ->
            val stepNumber = index + 1
            val isActive = currentStep >= stepNumber

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(if (isActive) cyanColor else Color.DarkGray)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    color = if (isActive) cyanColor else Color.DarkGray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (index < steps.size - 1) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .offset(y = (-10).dp)
                        .background(if (currentStep > stepNumber) cyanColor else Color.DarkGray)
                )
            }
        }
    }
}


@Composable
fun PersonalStep(firstName: String, onFirstNameChange: (String) -> Unit, lastName: String, onLastNameChange: (String) -> Unit, onNext: () -> Unit,onNavigateBack: () -> Unit, cyanColor: Color, darkBox: Color, lightGray: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(Res.string.register_personal_title), color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(stringResource(Res.string.register_personal_step), color = lightGray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Text(stringResource(Res.string.register_first_name), color = lightGray, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
        CustomTextField(value = firstName, onValueChange = onFirstNameChange, cyanColor = cyanColor, darkBox = darkBox)

        Spacer(modifier = Modifier.height(16.dp))

        Text(stringResource(Res.string.register_last_name), color = lightGray, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
        CustomTextField(value = lastName, onValueChange = onLastNameChange, cyanColor = cyanColor, darkBox = darkBox)

        Spacer(modifier = Modifier.height(40.dp))

        ActionButton(text = stringResource(Res.string.register_next_step), onClick = onNext, cyanColor = cyanColor)

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(stringResource(Res.string.register_have_account), color = lightGray, fontSize = 12.sp)
            Text(stringResource(Res.string.register_sign_in), color = cyanColor, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onNavigateBack() })
        }
    }
}


@Composable
fun ContactStep(email: String, onEmailChange: (String) -> Unit, phone: String, onPhoneChange: (String) -> Unit, onNext: () -> Unit, cyanColor: Color, darkBox: Color, lightGray: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(Res.string.register_contact_title), color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(stringResource(Res.string.register_contact_step), color = lightGray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Text(stringResource(Res.string.register_email), color = lightGray, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
        CustomTextField(value = email, onValueChange = onEmailChange, keyboardType = KeyboardType.Email, cyanColor = cyanColor, darkBox = darkBox)

        Spacer(modifier = Modifier.height(16.dp))

        Text(stringResource(Res.string.register_phone), color = lightGray, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
        CustomTextField(value = phone, onValueChange = onPhoneChange, keyboardType = KeyboardType.Phone, cyanColor = cyanColor, darkBox = darkBox)

        Spacer(modifier = Modifier.height(40.dp))

        ActionButton(text = stringResource(Res.string.register_next_step), onClick = onNext, cyanColor = cyanColor)
    }
}

@Composable
fun SecurityStep(
    password: String, 
    onPasswordChange: (String) -> Unit, 
    onRegister: () -> Unit, 
    isLoading: Boolean,
    isSuccess: Boolean,
    error: String?,
    cyanColor: Color, 
    darkBox: Color, 
    lightGray: Color
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(Res.string.register_security_title), color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(stringResource(Res.string.register_security_step), color = lightGray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Text(stringResource(Res.string.login_password_hint), color = lightGray, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
        CustomTextField(
            value = password,
            onValueChange = onPasswordChange,
            isPassword = true,
            cyanColor = cyanColor,
            darkBox = darkBox,
            imeAction = ImeAction.Done
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(stringResource(Res.string.register_password_rule), color = lightGray, fontSize = 12.sp)

        if (error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(error, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))

        val buttonWidth by animateDpAsState(
            targetValue = if (isSuccess || isLoading) 52.dp else 400.dp, // Ortalama ekran genisligi
            animationSpec = tween(durationMillis = 300)
        )

        val buttonColor by animateColorAsState(
            targetValue = if (isSuccess) Color(0xFF4CAF50) else cyanColor,
            animationSpec = tween(durationMillis = 300)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onRegister,
                modifier = Modifier
                    .width(buttonWidth)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(26.dp),
                enabled = !isLoading && !isSuccess,
                contentPadding = PaddingValues(0.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else if (isSuccess) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Success", tint = Color.White)
                } else {
                    Text(text = stringResource(Res.string.register_create_account), color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    cyanColor: Color,
    darkBox: Color
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = darkBox, unfocusedContainerColor = darkBox,
            focusedBorderColor = cyanColor, unfocusedBorderColor = Color.Transparent,
            focusedTextColor = Color.White, unfocusedTextColor = Color.White, cursorColor = cyanColor
        ),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction)
    )
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit, cyanColor: Color) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(52.dp),
        colors = ButtonDefaults.buttonColors(containerColor = cyanColor),
        shape = RoundedCornerShape(26.dp)
    ) {
        Text(text = text, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}