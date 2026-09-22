package com.globaljobai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Job(val title:String, val country:String, val salary:String, val match:Int, val visa:String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { GlobalJobAI() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalJobAI() {
    var screen by remember { mutableStateOf("Home") }
    val jobs = listOf(
        Job("Bicycle Mechanic", "🇯🇵 Japan", "¥220,000–¥300,000/month", 87, "Check employer"),
        Job("Maintenance Technician", "🇸🇬 Singapore", "S$2,200–S$3,000/month", 81, "Check employer"),
        Job("Production Technician", "🇰🇷 South Korea", "₩2.3M–₩3.0M/month", 76, "Check employer")
    )
    Scaffold(
        topBar = { TopAppBar(title = { Text("GlobalJob AI") }) },
        bottomBar = {
            NavigationBar {
                listOf("Home","Jobs","Applications","Profile").forEach { item ->
                    NavigationBarItem(selected=screen==item, onClick={screen=item},
                        icon={}, label={Text(item)})
                }
            }
        }
    ) { pad ->
        when(screen) {
            "Home" -> HomeScreen(pad, jobs)
            "Jobs" -> JobsScreen(pad, jobs)
            "Applications" -> ApplicationsScreen(pad)
            else -> ProfileScreen(pad)
        }
    }
}

@Composable
fun HomeScreen(pad: PaddingValues, jobs: List<Job>) {
    LazyColumn(Modifier.padding(pad).padding(16.dp), verticalArrangement=Arrangement.spacedBy(14.dp)) {
        item {
            Text("Find international jobs with AI", style=MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            Text("Upload your CV, set preferences, review matching jobs, and approve applications.")
        }
        item { Button(onClick={}) { Text("Upload CV") } }
        item { Text("Top matches", style=MaterialTheme.typography.titleLarge) }
        items(jobs.take(3)) { JobCard(it) }
    }
}

@Composable
fun JobsScreen(pad: PaddingValues, jobs: List<Job>) {
    LazyColumn(Modifier.padding(pad).padding(16.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) {
        item { Text("Matching Jobs", style=MaterialTheme.typography.headlineSmall) }
        items(jobs) { JobCard(it) }
    }
}

@Composable
fun JobCard(job: Job) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement=Arrangement.spacedBy(6.dp)) {
            Text(job.title, style=MaterialTheme.typography.titleMedium)
            Text(job.country)
            Text(job.salary)
            Text("AI profile match: ${job.match}%")
            Text("Visa sponsorship: ${job.visa}")
            Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick={}) { Text("View") }
                Button(onClick={}) { Text("Review") }
            }
        }
    }
}

@Composable
fun ApplicationsScreen(pad: PaddingValues) {
    Column(Modifier.padding(pad).padding(16.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) {
        Text("Applications", style=MaterialTheme.typography.headlineSmall)
        Text("No applications yet.")
        Text("Approved applications will appear here with status tracking.")
    }
}

@Composable
fun ProfileScreen(pad: PaddingValues) {
    Column(Modifier.padding(pad).padding(16.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) {
        Text("Profile", style=MaterialTheme.typography.headlineSmall)
        Text("CV: Not uploaded")
        Text("Preferred countries: Japan, South Korea, Singapore")
        Button(onClick={}) { Text("Set Preferences") }
    }
}
