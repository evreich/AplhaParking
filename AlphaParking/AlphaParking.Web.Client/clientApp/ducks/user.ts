import { AnyAction } from 'redux';
import { ThunkAction, ThunkDispatch } from 'redux-thunk';

import { AxiosError } from 'axios';
import AuthApi from '../api/Auth';
import * as Consts from '../constants/common';
import User from '../models/User';
import * as StorageUtils from '../utils/localStorageTools';
import { notifyError } from '../utils/request';
import { errorRequestAction, sendRequestAction, successRequestAction } from './request';

// action types
export const userActionTypes = {
    EDIT_USER: 'EDIT_USER',
    LOGIN: 'LOGIN',
    LOGOUT: 'LOGOUT'
    // CHECK_TOKEN
};

// actionCreators
export const logoutAction = (): ThunkAction<{}, {}, {}, AnyAction> =>
    (dispatch: ThunkDispatch<{}, {}, AnyAction>): {} => {
        localStorage.removeItem(Consts.JWT_TOKEN_KEY);
        return dispatch({ type: userActionTypes.LOGOUT });
    };

export const loginAction = (login: string, pass: string): ThunkAction<Promise<void>, {}, {}, AnyAction> =>
    (dispatch: ThunkDispatch<{}, {}, AnyAction>): Promise<void> => {
        dispatch(sendRequestAction());
        return AuthApi.login(login, pass)
            .then((response) => response.data)
            .then((payload) => {
                dispatch({
                    payload: {
                        address: payload.address,
                        email: payload.email,
                        fio: payload.fio,
                        id: payload.id,
                        jwtToken: payload.access_token,
                        login: payload.login,
                        phone: payload.phone
                    },
                    type: userActionTypes.LOGIN
                });
                StorageUtils.setItem(Consts.JWT_TOKEN_KEY, payload.access_token);
                dispatch(successRequestAction());
            })
            .catch((error: AxiosError) => notifyError(error, dispatch));
    };

export const registrAction = (user: User): ThunkAction<Promise<void>, {}, {}, AnyAction> =>
    (dispatch: ThunkDispatch<{}, {}, AnyAction>): Promise<void> => {
        dispatch(sendRequestAction());
        return AuthApi.registration(user)
            .then((response) => response.data)
            .then((payload) => {
                dispatch({
                    payload: {
                        address: payload.address,
                        email: payload.email,
                        fio: payload.fio,
                        id: payload.id,
                        jwtToken: payload.access_token,
                        login: payload.login,
                        phone: payload.phone
                    },
                    type: userActionTypes.LOGIN
                });
                StorageUtils.setItem(Consts.JWT_TOKEN_KEY, payload.access_token);
                dispatch(successRequestAction());
            })
            .catch((error: AxiosError) => notifyError(error, dispatch));
    };

// reducer
const initState = {
    address: '',
    email: '',
    fio: '',
    id: 0,
    jwtToken: StorageUtils.getItem(Consts.JWT_TOKEN_KEY),
    login: '',
    phone: ''
};

const requestReducer = (state = initState, action: any) => {
    switch (action.type) {
        case userActionTypes.LOGOUT:
            return {
                address: '',
                email: '',
                fio: '',
                id: 0,
                jwtToken: null,
                login: '',
                phone: ''
            };
        case userActionTypes.LOGIN:
            return {
                ...action.payload
            };
        case userActionTypes.EDIT_USER:
            return {
                ...action.payload
            };
        default:
            return state;
    }
};

export default requestReducer;