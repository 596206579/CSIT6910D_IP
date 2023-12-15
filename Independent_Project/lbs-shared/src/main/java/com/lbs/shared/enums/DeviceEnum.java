package com.lbs.shared.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Eric Xue
 * @date 2021/05/31
 */
@RequiredArgsConstructor
@Getter
public enum DeviceEnum {
    IOS("IOS"),
    ANDROID("安卓");

    private final String message;
}
