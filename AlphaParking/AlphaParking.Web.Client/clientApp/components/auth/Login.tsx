import * as React from 'react';
import { Button } from 'react-bootstrap';
import { RouteComponentProps, withRouter } from 'react-router-dom';

import LoginForm from './LoginForm';

// tslint:disable-next-line:no-empty-interface
interface ILoginProps extends RouteComponentProps { }

const Login: React.SFC<ILoginProps> = (props) => {
    const onVkRegBtnClick = () => props.history.push('/registration');
    return <>
        <h2>Страница входа</h2>
        <br />
        <LoginForm />
        <hr />
        <Button bsStyle='primary' bsSize='large' onClick={onVkRegBtnClick}>
            Регистрация
        </Button>
    </>;
};

export default withRouter(Login);