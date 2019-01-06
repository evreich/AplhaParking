import axios, { AxiosPromise, AxiosRequestConfig } from 'axios';

import * as commonConsts from '../constants/common';
import requestContentTypes from '../constants/requestContentTypes';
import User from '../models/User';
import * as requestUtils from '../utils/request';

export default class AuthApi {
    static login = (login: string, pass: string): AxiosPromise => {
        let headers = {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': requestContentTypes.JSON_DATA,
            'mode': 'cors'
        };
        const auth = requestUtils.getAuthHeader();
        if (auth)
            headers = {
                ...headers,
                ...auth
            };

        const config: AxiosRequestConfig = {
            headers
        };

        const body = {
            login,
            pass
        };

        return axios.post(`${commonConsts.SERVER_API}/login`, body, config);
    }

    static registration = (user: User): AxiosPromise => {
        let headers = {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': requestContentTypes.JSON_DATA,
            'mode': 'cors'
        };
        const auth = requestUtils.getAuthHeader();
        if (auth)
            headers = {
                ...headers,
                ...auth
            };

        const config: AxiosRequestConfig = {
            headers
        };

        const body = JSON.parse(user.toJSON());

        return axios.post(`${commonConsts.SERVER_API}/registration`, body, config);
    }
}