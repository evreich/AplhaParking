import axios, { AxiosError, AxiosPromise, AxiosRequestConfig } from 'axios';

import { Dispatch } from 'redux';
import * as commonConsts from '../constants/common';
import requestContentTypes from '../constants/requestContentTypes';
import { sendRequestAction, successRequestAction } from '../ducks/request';
import { editUserAction } from '../ducks/user';
import User from '../models/User';
import * as requestUtils from '../utils/request';

export default class AuthApi {
    static getUserInfo = (userId: number, dispatch: Dispatch): Promise<void> => {
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
            headers,
            params: {
                userId
            }
        };

        dispatch(sendRequestAction());
        return axios.get(`${commonConsts.SERVER_API}/user`, config)
            .then((response) => response.data)
            .then((payload) => {
                dispatch(successRequestAction());
                dispatch(editUserAction(payload));
            })
            .catch((error: AxiosError) => {
                requestUtils.notifyError(error, dispatch);
            });
    }

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

        const body = JSON.parse(user.getJSON());

        return axios.post(`${commonConsts.SERVER_API}/registration`, body, config);
    }
}