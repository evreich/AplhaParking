import { AxiosError } from 'axios';
import { Dispatch } from 'redux';

import * as Consts from '../constants/common';
import { clearErrorAction, errorRequestAction } from '../ducks/request';
import { getItem } from './localStorageTools';

export const getAuthHeader = () => {
    const token = getItem(Consts.JWT_TOKEN_KEY);
    return token ? { Authorization: `Bearer ${token}` } : null;
};

export const notifyError = (error: AxiosError, dispatch: Dispatch) => {
    if (error.response && error.response.data)
        dispatch(errorRequestAction(error.response.data.error));
    else
        dispatch(errorRequestAction(error.message));
    setTimeout(() => dispatch(clearErrorAction()), 5000);
};