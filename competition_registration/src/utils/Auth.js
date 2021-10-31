export function getToken() {
    let tokenDetails = getByStorage("tokenDetails");
    if (tokenDetails != undefined && tokenDetails != "undefined") {
        tokenDetails = JSON.parse(tokenDetails);
    }
    if (tokenDetails == null || tokenDetails == undefined || tokenDetails == "undefined") {
        return '';
    }
    return tokenDetails.token_type + " " + tokenDetails.access_token;
}

/**
 * vuex存储在内存
 * localstorage（本地存储）则以文件的方式存储在本地,永久保存
 * sessionstorage( 会话存储 ) ,临时保存。localStorage和sessionStorage只能存储字符串类型
 */
export function getByStorage(key) {
    return sessionStorage.getItem(key);
}

export function setToStorage(key, value) {
    return sessionStorage.setItem(key, value);
}