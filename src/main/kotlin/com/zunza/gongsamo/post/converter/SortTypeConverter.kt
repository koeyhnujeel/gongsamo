package com.zunza.gongsamo.post.converter

import com.zunza.gongsamo.post.constant.SortType
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class SortTypeConverter : Converter<String, SortType> {
    override fun convert(source: String): SortType? {
        return SortType.from(source)
    }
}
