# 航班時刻表與匯率查詢應用程式

這是一個使用 Jetpack Compose 開發的 Android 應用程式，顯示航班時刻表與匯率資訊。應用程式提供即時航班資訊更新和最新的匯率顯示。

## 功能特色

### 航班資訊
- 每 10 秒自動更新即時航班時刻表  
- 顯示進港和出港航班資訊  
- 航班狀態追蹤  
- 處理加載和錯誤狀態  

### 匯率查詢
- 提供多種貨幣的即時匯率  
- 乾淨且卡片式的 UI 設計  
- 錯誤處理與重試機制  
- 排序後的匯率清單顯示  

## 技術

### 核心函式庫
- **Jetpack Compose**：現代 Android UI 工具包  
- **Material 3**：最新的 Material Design 元件  
- **Kotlin Coroutines**：非同步程式設計  
- **Kotlin Flow**：響應式程式設計  

### 網路層
- **Retrofit2**：REST API 客戶端  
- **Moshi**：JSON 解析  
- **OkHttp3**：HTTP 客戶端  
- **Logging Interceptor**：網路請求日誌紀錄  

### 架構元件
- **ViewModel**：UI 狀態管理  
- **Navigation Compose**：應用內導航  
- **Lifecycle Components**：生命週期感知元件  

## 專案結構

### UI 元件
- `FlightScheduleScreen`：主要畫面，包含航班資訊的分頁  
- `FlyInfoInScreen`：顯示進港航班  
- `FlyInfoOutScreen`：顯示出港航班  
- `CurrencyScreen`：顯示匯率資訊  

### 通用 UI 元件
- 加載指示器  
- 帶重試功能的錯誤訊息  
- 航班與匯率列表顯示  

## 設定指南

### 依賴項
```gradle
dependencies {
    // AndroidX 與 Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // 網路
    implementation(libs.com.squareup.retrofit2)
    implementation(libs.com.squareup.moshi)
    implementation(libs.com.squareup.converter.moshi)
    implementation(libs.com.squareup.moshi.kotlin)
    implementation(libs.com.squareup.okhttp3.logging.interceptor)
    ksp(libs.com.squareup.moshi.kotlin.codegen)

    // 協程與導航
    implementation(libs.kotlinx.coroutines)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
}
```

## 功能實現

### 航班時刻表
- 每 10 秒自動更新數據  
- 分頁顯示進港和出港航班  
- 在切換頁面時自動停止輪詢  
- 集中化的錯誤處理  

### 匯率查詢
- 乾淨的卡片式匯率 UI  
- 下拉刷新功能  
- 帶重試選項的錯誤處理  
- 格式化的匯率顯示（小數點後兩位）  

## 狀態管理

應用程式使用密封類別管理 UI 狀態：
```kotlin
sealed class FlightScheduleUiState {
    object Loading : FlightScheduleUiState()
    data class Success(val flights: List<FlightInfo>) : FlightScheduleUiState()
    data class Error(val message: String) : FlightScheduleUiState()
}
```
