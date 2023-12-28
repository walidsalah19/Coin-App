package com.app.coin.Data.DataSource.DataClass.CoinDetailsDataClass

import com.app.coin.Domain.Modeles.CoinDetail

data class CoinDetails(
    val additional_notices: List<Any>,
    val asset_platform_id: Any,
    val block_time_in_minutes: Int,
    val categories: List<String>,
    val community_data: CommunityData,
    val country_origin: String,
    val description: Description,
    val detail_platforms: DetailPlatforms,
    val developer_data: DeveloperData,
    val genesis_date: String,
    val hashing_algorithm: String,
    val id: String,
    val image: Image,
    val last_updated: String,
    val links: Links,
    val localization: Localization,
    val market_cap_rank: Int,
    val market_data: MarketData,
    val name: String,
    val platforms: Platforms,
    val preview_listing: Boolean,
    val public_notice: Any,
    val sentiment_votes_down_percentage: Double,
    val sentiment_votes_up_percentage: Double,
    val status_updates: List<Any>,
    val symbol: String,
    val tickers: List<Ticker>,
    val watchlist_portfolio_users: Int,
    val web_slug: String
){
    fun toCoinDetails(): CoinDetail {
        return CoinDetail(
            name,
            image.large,
            market_data.market_cap.usd.toDouble(),
            market_data.current_price.usd.toDouble(),
            market_data.price_change_percentage_24h_in_currency.usd,
            market_data.low_24h.usd.toDouble(),
            market_data.high_24h.usd.toDouble(),
            description.en,
        )
    }
}