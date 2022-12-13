package com.rustorepurchase

import com.facebook.react.bridge.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.model.product.ProductsResponse

class RuStoreModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    init {
      val ai: ApplicationInfo = reactContext.packageManager
        .getApplicationInfo(reactContext.packageName, PackageManager.GET_META_DATA)
      val consoleApplicationId = ai.metaData["consoleApplicationId"]
      val deeplinkPrefix = ai.metaData["deeplinkPrefix"]
      val applicationId = ai.metaData["applicationId"]

      RuStoreBillingClient.init(
        appContext = reactContext,
        applicationId = applicationId, //идентификатор приложения;
        consoleApplicationId = consoleApplicationId, //код приложения из системы RuStore Консоль;
        deeplinkPrefix = deeplinkPrefix //URL для использования deeplink.
      )
    }

    override fun getName() = "RuStoreModule"

    //Получение актуальной информации по списку продуктов
    @ReactMethod public fun getProducts(productIds: List<String>, callback: Callback, callbackError: Callback){
        try {
            runBlocking {
                withContext(Dispatchers.IO) {
                    val productsResponse: ProductsResponse =
                        RuStoreBillingClient.products.getProducts(
                            productIds = productIds //список идентификаторов продуктов.
                        )

                    callback.invoke(productsResponse)
                }
            }
        }catch (Exceptin ex){callbackError.invoke(ex.getMessage())}
    }
}
