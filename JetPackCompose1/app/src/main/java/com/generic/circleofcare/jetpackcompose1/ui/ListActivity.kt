package com.generic.circleofcare.jetpackcompose1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.generic.circleofcare.jetpackcompose1.R

class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            val scrollState = rememberScrollState()
            LazyColumn{//(modifier =Modifier.verticalScroll(scrollState)){
                itemsIndexed(
                    listOf("this","is","world", "of" , "technology.", "this","is","world", "of" , "technology.", "we","are","using", "compose", "for", "application." )
                )  {
                        _, string->
                    Text(
                        text = string,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 24.dp),
                        fontSize =24.sp,
                        fontWeight =FontWeight.Bold)
                }
//                items(500){
//                        Text(
//                            text = "Item $it",
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(vertical = 24.dp),
//                            fontSize =24.sp,
//                            fontWeight =FontWeight.Bold)
//                    }
            }

        }
    }
}