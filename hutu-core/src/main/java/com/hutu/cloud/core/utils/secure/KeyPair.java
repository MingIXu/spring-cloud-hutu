package com.hutu.cloud.core.utils.secure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * KeyPair
 *
 * @author hutu
 * @date 2022/6/22
 */
@Getter
@AllArgsConstructor
@ToString
public class KeyPair {
    String privateKey;
    String publicKey;
}
