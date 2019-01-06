// action types
export const requestActionTypes = {
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
        default:
            return state;
    }
};

export default requestReducer;