package com.miruni.feature.mypage.domain

import androidx.annotation.DrawableRes
import com.miruni.feature.mypage.R

/**
 * 프로필 이미지 도메인 모델
 * @param resId 로컬 drawable 리소스 ID
 * @param apiValue 서버 API에서 사용하는 프로필 이미지 값
 */
data class ProfileImage(
    @DrawableRes val resId: Int,
    val apiValue: String
)

/**
 * 프로필 이미지 목록
 * 로컬 drawable과 API 값을 매핑
 */
fun getProfileImages(): List<ProfileImage> = listOf(
    ProfileImage(resId = R.drawable.betty, apiValue = "BETTY"),
    ProfileImage(resId = R.drawable.janet, apiValue = "JANET"),
    ProfileImage(resId = R.drawable.jonas, apiValue = "JONAS"),
    ProfileImage(resId = R.drawable.mark, apiValue = "MARK"),
    ProfileImage(resId = R.drawable.tracy, apiValue = "TRACY"),
)

/**
 * API 값으로 프로필 이미지 인덱스 찾기
 * @param apiValue 서버에서 받은 프로필 이미지 값
 * @return 해당 프로필 이미지의 인덱스, 없으면 0 반환
 */
fun findProfileImageIndex(apiValue: String): Int {
    val images = getProfileImages()
    val index = images.indexOfFirst { it.apiValue == apiValue }
    return if (index >= 0) index else 0
}
