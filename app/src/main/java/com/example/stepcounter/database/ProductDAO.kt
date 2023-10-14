package com.example.stepcounter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stepcounter.database.entities.ProductInfo

@Dao
interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(productInfo: ProductInfo)

    @Query("SELECT * FROM productInfo WHERE barcode = :barcode")
    fun getScannedProductByBarcode(barcode: String): LiveData<ProductInfo>

    @Query("SELECT * FROM productInfo")
    fun getAllProducts(): LiveData<List<ProductInfo>>

    @Query("SELECT * FROM productInfo WHERE productName = :productName")
    fun getProductsByName(productName: String): LiveData<List<ProductInfo>>
}