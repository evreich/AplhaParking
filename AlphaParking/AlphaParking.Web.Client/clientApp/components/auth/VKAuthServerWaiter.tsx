import * as React from 'react';
import { RouteComponentProps, withRouter } from 'react-router-dom';

import * as Consts from '../../constants/common';

// tslint:disable-next-line:no-empty-interface
interface IProps extends RouteComponentProps { }

interface IState {
    loading: boolean;
    requestSended: boolean;
    error: string;
}

class VKAuthServerWaiter extends React.Component<IProps, IState> {
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
            .then((payload: {access_token: string}) => this.setState({
                loading: false
            },
                () => localStorage.setItem('access_token', payload.access_token))
            )
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
            return <h2>Ожидание ответа от сервера...</h2>;
        if (!loading && requestSended)
            history.push('/hello');
        return null;
    }
}

export default withRouter(VKAuthServerWaiter);