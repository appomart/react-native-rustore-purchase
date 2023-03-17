package com.rustorepurchase

import com.facebook.react.bridge.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.model.product.ProductsResponse
import ru.rustore.sdk.core.tasks.OnCompleteListener

class RuStoreModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    init {
      val ai: ApplicationInfo = reactContext.packageManager
        .getApplicationInfo(reactContext.packageName, PackageManager.GET_META_DATA)
      val consoleApplicationId = ai.metaData["consoleApplicationId"]
      val deeplinkPrefix = ai.metaData["deeplinkPrefix"]
      val applicationId = ai.metaData["applicationId"]

      RuStoreBillingClient.init(
        application = reactContext,
        consoleApplicationId = consoleApplicationId, //код приложения из системы RuStore Консоль;
        deeplinkScheme = deeplinkPrefix //URL для использования deeplink.
      )
    }

    override fun getName() = "RuStoreModule"

    //Получение актуальной информации по списку продуктов
    /*@ReactMethod public fun getProducts(productIds: List<String>, callback: Callback, callbackError: Callback){
        try {
            runBlocking {
                withContext(Dispatchers.IO) {
                    val productsResponse: ProductsResponse =
                        RuStoreBillingClient.products.getProducts(
                            productIds = productIds //список идентификаторов продуктов.
                        ).await()

                    callback.invoke(productsResponse)
                }
            }
        }catch (ex: Exception){callbackError.invoke(ex.getMessage())}
    }*/

    @ReactMethod public fun getProducts(productIds: List<String>, callback: Callback, callbackError: Callback){
        RuStoreBillingClient.products.getProducts(productIds = productIds)
            .addOnCompleteListener(object : OnCompleteListener<ProductsResponse> {
                override fun onSuccess(result: ProductsResponse) {
                    callback.invoke(result)
                }

                override fun onFailure(throwable: Throwable) {
                    // Process error
                    if(throwable.message != null && throwable.message!!.isNotEmpty())
                        callbackError.invoke(throwable.message)
                    else if(throwable.cause != null && throwable.cause!!.message != null && throwable.cause!!.message!!.isNotEmpty())
                        callbackError.invoke(throwable.cause!!.message)
                }
            })
    }
}
