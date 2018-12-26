import * as React from 'react';
import { Button } from 'react-bootstrap';
import { Redirect } from 'react-router';

export default class Login extends React.Component {
    render() {
        return <>
            <div>Страница входа</div>
            <br />
            <p>Место для формы логина</p>
            <hr />
            <Button  bsStyle='primary' bsSize='large' onClick={() => <Redirect to='/registration'/>}>
                Регистрация
            </Button>
         </>;
    }
}