
export const VK_CLIENT_ID = 6798414;
export const VK_AUTH_HOST = 'https://oauth.vk.com/authorize';
export const REDIRECT_URL = 'http://localhost:8383/vk/auth';
export const RESPONSE_TYPE = 'code';
export const VERSION_VK = 5.92;

export const OAUTH_VK_AUTH = `${VK_AUTH_HOST}?client_id=${VK_CLIENT_ID}&display=page&` +
    `redirect_url=${REDIRECT_URL}&scope=email&response_type=${RESPONSE_TYPE}&v=${VERSION_VK}`;