import axios, { AxiosError, AxiosPromise, AxiosRequestConfig } from 'axios';

import { Dispatch } from 'redux';
import * as commonConsts from '../constants/common';
import requestContentTypes from '../constants/requestContentTypes';
import { sendRequestAction, successRequestAction } from '../ducks/request';
import Car from '../models/Car';
import * as requestUtils from '../utils/request';

export default class CarApi {
    static getUserCars = (userId: number, dispatch: Dispatch): Promise<Car[]> => {
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
        return axios.get(`${commonConsts.SERVER_API}/user/cars`, config)
            .then((response) => response.data)
            .then((payload) => {
                dispatch(successRequestAction());
                return payload;
            })
            .catch((error: AxiosError) => {
                requestUtils.notifyError(error, dispatch);
            });
    }
}