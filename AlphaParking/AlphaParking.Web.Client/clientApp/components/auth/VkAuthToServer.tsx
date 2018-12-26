import * as React from 'react';
import { RouteComponentProps, withRouter } from 'react-router-dom';

import consts from '../../constants';

// tslint:disable-next-line:no-empty-interface
interface IProps extends RouteComponentProps { }

interface IState {
    loading: boolean;
    requestSended: boolean;
}

class VkAuthToServer extends React.Component<IProps, IState> {
    constructor(props: any) {
        super(props);

        this.state = {
            loading: false,
            requestSended: false
        };
    }

    sendRequestToServer = (code: string) => {
        const confToken: RequestInit = {
            method: 'GET',
            mode: 'cors'
        };
        fetch(`${consts.SERVER_API}?code=${code}`, confToken)
            .then((response) =>
                response.ok && response.json()
            )
            .then((accessToken) => this.setState({
                loading: false
            },
                () => localStorage.setItem('access_token', accessToken))
            )
            .catch((err) => err);
    }

    render() {
        const { loading, requestSended } = this.state;
        const { location: { search }, history } = this.props;
        if (!requestSended)
            this.setState({
                loading: true,
                requestSended: true
            }, () => this.sendRequestToServer(search.split('=')[1].toString()));
        if (loading && requestSended)
            return <div>Ожидание ответа от сервера...</div>;
        if (!loading && requestSended)
            history.push('/hello');
        return null;
    }
}

export default withRouter(VkAuthToServer);