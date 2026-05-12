package com.example.nammasantheledger.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

@Composable
fun CustomerDetailScreen(
    name: String,
    phone: String,
    balance: Double,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onUpdate: () -> Unit,
    onWhatsApp: () -> Unit
) {
    val displayBalance = abs(balance)
    val statusText = when {
        balance > 0 -> "You will Receive"
        balance < 0 -> "You will Give (Advance)"
        else -> "Settled"
    }
    val statusColor = when {
        balance > 0 -> Color(0xFF2E7D32) // Green
        balance < 0 -> Color(0xFFD32F2F) // Red
        else -> Color.Gray
    }

    AppBackground {
        Column(modifier = Modifier.fillMaxSize()) {

            // 🔷 HEADER
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(if (balance >= 0) MaterialTheme.colorScheme.primary else Color(0xFFD32F2F))
                    .padding(bottom = 24.dp)
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Text("Ledger Details", color = Color.White, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick = onEdit) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Color.White
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier.size(100.dp),
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.2f)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.padding(20.dp),
                            tint = Color.White
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Text(name, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)

                    Surface(
                        color = Color.White.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            statusText,
                            color = statusColor,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        QuickActionItem(Icons.Default.Call, "Call")
                        QuickActionItem(Icons.Default.Email, "Email")
                        QuickActionItem(Icons.AutoMirrored.Filled.Send, "WhatsApp", onClick = onWhatsApp)
                        QuickActionItem(Icons.Default.Star, "Favorite")
                    }
                }
            }

            // 🔻 DETAILS
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {

                DetailRow(
                    icon = Icons.Default.Phone,
                    label = "Mobile",
                    primaryText = phone,
                    secondaryText = "Contact Information"
                )

                DetailRow(
                    icon = Icons.Default.AccountBalanceWallet,
                    label = "Net Balance",
                    primaryText = "₹$displayBalance",
                    primaryTextColor = statusColor,
                    secondaryText = if (balance >= 0) "Customer owes you" else "You owe Customer (Extra Paid)"
                )

                Spacer(Modifier.weight(1f))

                // 🔘 BUTTONS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    OutlinedButton(
                        onClick = onUpdate,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (balance > 0) "Collect Payment" else "Add Credit")
                    }

                    Button(
                        onClick = onWhatsApp,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (balance > 0) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    ) {
                        Text("Send Reminder")
                    }
                }
            }
        }
    }
}

//////////////////////////////////////////////////////
// 🔹 REUSABLE COMPONENTS
//////////////////////////////////////////////////////

@Composable
fun QuickActionItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit = {}
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Surface(
            modifier = Modifier.size(50.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color.White.copy(alpha = 0.15f),
            onClick = onClick
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.padding(12.dp))
        }

        Spacer(Modifier.height(4.dp))

        Text(label, color = Color.White, fontSize = 11.sp)
    }
}

@Composable
fun DetailRow(
    icon: ImageVector,
    label: String,
    primaryText: String,
    primaryTextColor: Color = Color.Unspecified,
    secondaryText: String
) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(label, fontWeight = FontWeight.Bold)
        }

        Column(modifier = Modifier.padding(start = 28.dp)) {
            Text(secondaryText, fontSize = 11.sp, color = Color.Gray)
            Text(primaryText, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = primaryTextColor)
        }

        HorizontalDivider(modifier = Modifier.padding(top = 8.dp), thickness = 0.5.dp)
    }
}

//////////////////////////////////////////////////////
// 👀 PREVIEW
//////////////////////////////////////////////////////

@Preview(showSystemUi = true)
@Composable
fun CustomerDetailPreview() {
    CustomerDetailScreen(
        name = "Sirisha",
        phone = "9876543210",
        balance = -400.0, // Example: You owe them (Advance)
        onBack = {},
        onEdit = {},
        onUpdate = {},
        onWhatsApp = {}
    )
}
