import * as React from 'react';
import { Button } from 'react-bootstrap';
import { RouteComponentProps, withRouter } from 'react-router-dom';

import LoginForm from './forms/LoginForm';
import VKAuthButton from './VKAuthButton';

// tslint:disable-next-line:no-empty-interface
interface ILoginProps extends RouteComponentProps { }

const Login: React.SFC<ILoginProps> = (props) => {
    const onVkRegBtnClick = () => props.history.push('/registration');
    const regBtn = <div style={{display: 'flex'}}><Button
            style={{display: 'inline-block', marginRight: '4px'}}
            bsStyle='primary'
            bsSize='large'
            onClick={onVkRegBtnClick}>
            Регистрация
        </Button>
        <VKAuthButton text={'Вход VK'} />
    </div>;
    return <>
        <h2>Форма входа</h2>
        <br />
        <LoginForm regBtn={regBtn} />
    </>;
};

export default withRouter(Login);