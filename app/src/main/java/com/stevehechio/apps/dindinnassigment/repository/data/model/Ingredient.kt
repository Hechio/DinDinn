package com.stevehechio.apps.dindinnassigment.repository.data.model

/**
 * Created by stevehechio on 6/27/21
 */

//this is the response{
//    "id": 1,
//    "longDesc": "Enjoy servered beef chillie and vegetable samosas",
//    "name": "Beef Chillie Cooked Samosas",
//    "available": 50,
//    "category": "main",
//    "shortDesc": "A pack of 10 best samosas in town",
//    "status": "A",
//    "url": "https://firebasestorage.googleapis.com/v0/b/vellofood.appspot.com/o/COOKED%20SAMOSAS.jpg?alt=media&token=f3931796-08b5-4feb-9173-1d6210dca4be"
//}
data class Ingredient(val id: Long, val name: String, val available: Int,
                      val category: String, val url: String)