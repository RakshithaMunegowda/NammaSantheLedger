package com.example.nammasantheledger.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammasantheledger.R
import kotlin.math.abs

// ✅ NEW DATA MODEL (dynamic)
data class CustomerLedgerUI(
    val name: String,
    val phone: String,
    val balance: Double,
    val totalOrders: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerScreen(
    customers: List<CustomerLedgerUI>,
    onBack: () -> Unit,
    onClick: (String) -> Unit
) {

    var searchQuery by remember { mutableStateOf("") }

    val filtered = customers.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    AppBackground {
        Column(modifier = Modifier.fillMaxSize()) {

            // 🔙 Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                }
                Text(
                    stringResource(R.string.customers),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // 🔍 Search
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF7F8F9),
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(Modifier.height(10.dp))

            // 📋 List
            LazyColumn {
                items(filtered) { customer ->
                    CustomerListItem(customer, onClick)
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        thickness = 0.5.dp,
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomerListItem(
    customer: CustomerLedgerUI,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(customer.name) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = customer.name.take(1),
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {

            Text(customer.name, fontWeight = FontWeight.Bold)

            Text(
                text = customer.phone,
                fontSize = 12.sp,
                color = Color.Gray
            )

            val displayBalance = abs(customer.balance)
            val balanceColor = if (customer.balance >= 0) Color(0xFF2E7D32) else Color(0xFFD32F2F)
            val balanceLabel = if (customer.balance >= 0) "Get: " else "Give: "

            Text(
                text = "$balanceLabel ₹$displayBalance",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = balanceColor
            )

            Text(
                "Total Transactions: ${customer.totalOrders}",
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
    }
}

//////////////////////////////////////////////////////////////
// ✅ PREVIEW (SAFE)
//////////////////////////////////////////////////////////////

@Preview(showSystemUi = true)
@Composable
fun CustomerPreview() {

    val sample = listOf(
        CustomerLedgerUI("Ravi", "9876543210", 500.0, 3),
        CustomerLedgerUI("Sita", "8765432109", -200.0, 2)
    )

    CustomerScreen(
        customers = sample,
        onBack = {},
        onClick = {}
    )
}
