// action types
export const requestActionTypes = {
    CLEAR_ERROR: 'CLEAR_ERROR',
    REQUEST_ERROR: 'REQUEST_ERROR',
    REQUEST_SENDED: 'REQUEST_SENDED',
    REQUEST_SUCCESS: 'REQUEST_SUCCESS'
};

// actionCreators
export const sendRequestAction = () => ({ type: requestActionTypes.REQUEST_SENDED });
export const successRequestAction = () => ({ type: requestActionTypes.REQUEST_SUCCESS });
export const errorRequestAction = (error: string) => ({
    payload: { error },
    type: requestActionTypes.REQUEST_ERROR
});
export const clearErrorAction = () => ({ type: requestActionTypes.CLEAR_ERROR});

// reducer
const initState = {
    error: '',
    isFetching: false
};

const requestReducer = (state = initState, action: any) => {
    switch (action.type) {
        case requestActionTypes.REQUEST_SENDED:
            return {
                error: '',
                isFetching: true
            };
        case requestActionTypes.REQUEST_SUCCESS:
            return {
                ...state,
                isFetching: false
            };
        case requestActionTypes.REQUEST_ERROR:
            const { error } = action.payload;
            return {
                error,
                isFetching: false
            };
        case requestActionTypes.CLEAR_ERROR:
            return { error: ''};
        default:
            return state;
    }
};

export default requestReducer;