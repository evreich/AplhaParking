import * as React from 'react';
import { RouteComponentProps, withRouter } from 'react-router-dom';

import { connect } from 'react-redux';
import { Dispatch } from 'redux';
import { ThunkDispatch } from 'redux-thunk';
import * as Consts from '../../constants/common';
import { vkAuthAction } from '../../ducks/user';
import LoaderComponent from '../common/Loader';

interface IState {
    loading: boolean;
    requestSended: boolean;
    error: string;
}

interface IMapDispatchToProps {
    login: (payload: any) => void;
}

class VKAuthServerWaiter extends React.Component<RouteComponentProps<any> & IMapDispatchToProps, IState> {
    constructor(props: any) {
        super(props);

        this.state = {
            error: '',
            loading: false,
            requestSended: false
        };
    }

    sendRequestToServer = (code: string) => {
        const confToken: RequestInit = {
            body: code,
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json; charset=UTF-8'
            },
            method: 'POST',
            mode: 'cors'
        };
        fetch(`${Consts.SERVER_API}/vk/auth`, confToken)
            .then((response) => {
                if (response.ok)
                    return response.json();
                else
                    throw new Error(`Произошла ошибка. Статус ошибки: ${response.status}`);
            })
            .then((payload) => this.setState({
                loading: false
                },
                () => {
                    const { login } = this.props;
                    login(payload);
                }
            ))
            .catch((err) => this.setState({
                error: err.message
            }));
    }

    render() {
        const { loading, requestSended, error } = this.state;
        const { location: { search }, history } = this.props;
        if (error)
            return <h2 style={{color: 'red'}}>{error}</h2>;
        if (!requestSended)
            this.setState({
                loading: true,
                requestSended: true
            }, () => this.sendRequestToServer(search.split('=')[1].toString()));
        if (loading && requestSended)
            return <LoaderComponent />;
        if (!loading && requestSended)
            history.push('/cars');
        return null;
    }
}

const mapDispatchToProps = (dispatch: Dispatch): IMapDispatchToProps => {
    return {
        login: (payload) => dispatch(vkAuthAction(payload))
    };
};

export default withRouter(connect(undefined, mapDispatchToProps)(VKAuthServerWaiter)as React.ComponentType<any>);