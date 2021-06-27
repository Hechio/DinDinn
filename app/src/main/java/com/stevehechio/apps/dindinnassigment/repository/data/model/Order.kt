package com.stevehechio.apps.dindinnassigment.repository.data.model

/**
 * Created by stevehechio on 6/27/21
 */

//"id": 11,
//"title": "Chicken Noodle",
//"addon": [
//{
//    "id": 26,
//    "title": "Extra chicken",
//    "quantity": 2
//},
//{
//    "id": 27,
//    "title": "Sambal",
//    "quantity": 1
//}
//],
//"quantity": 1,
//"created_at": "2021-06-10T15:10+00Z",
//"alerted_at": "2021-06-10T15:13+00Z",
//"expired_at": "2021-06-10T15:15+00Z"
//}
data class Order (
    val id : Long,
    val title: String,
    val quantity: Int,
    val addon: List<Addon>,
    val created_at: String,
    val alerted_at: String)

data class Addon( val id : Long, val title: String, val quantity: Int)