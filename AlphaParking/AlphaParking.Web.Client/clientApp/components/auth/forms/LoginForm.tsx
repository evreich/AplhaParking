import * as React from 'react';
import { Button, FormControl } from 'react-bootstrap';
import { connect } from 'react-redux';
import { ThunkDispatch } from 'redux-thunk';

import { loginAction } from '../../../ducks/user';
import FieldGroup from '../../common/FieldGroup';

interface ILoginFormState {
    login: string;
    password: string;
    error: string;
}

interface IMapDispatchToProps {
    login: (login: string, pass: string) => void;
}

interface IProps {
    regBtn: JSX.Element;
}

class LoginForm extends React.Component<IMapDispatchToProps & IProps, ILoginFormState> {
    constructor(props: any) {
        super(props);
        this.state = {
            error: '',
            login: '',
            password: ''
        };
    }

    loginHandler = (event: React.FormEvent<FormControl>) => {
        const { error } = this.state;
        const value = (event.target as HTMLInputElement).value;
        if (value.length < 4)
            this.setState({
                error: 'Длина логина должна быть >= 4'
            });
        else
            if (error)
                this.setState({ error: '' });
        this.setState({
            login: value
        });
    }

    passwordHandler = (event: React.FormEvent<FormControl>) => {
        const { error } = this.state;
        const english = /^[A-Za-z0-9]*$/;
        const value = (event.target as HTMLInputElement).value;
        if (!english.test(value))
            this.setState({
                error: 'Пароль должен состоять из английский букв и цифр [A-Za-z0-9]'
            });
        else
            if (error)
                this.setState({ error: '' });
        if (value.length < 4)
            this.setState({
                error: 'Длина пароля должна быть >= 4'
            });
        else
            if (error)
                this.setState({ error: '' });
        this.setState({
            password: value
        });
    }

    loginBtnClickHandler = (event: React.MouseEvent<Button>) => {
        const { login, password: pass, error } = this.state;
        const { login: loginRequest } = this.props;
        if (!error && login !== '' && pass !== '')
            loginRequest(login, pass);
    }

    render() {
        const { login, password, error } = this.state;
        const { regBtn } = this.props;
        const disableBtn = !!error || login === '' || password === '';
        return <div className='main'>
            <FieldGroup
                id='formControlsText'
                type='text'
                value={login}
                label='Логин'
                onChange={this.loginHandler}
                placeholder='Например, ivanov95'
            />
            <FieldGroup
                id='formControlsPassword'
                label='Пароль'
                value={password}
                onChange={this.passwordHandler}
                type='password'
                placeholder='Например, ivanovPass' />
            <div style={{display: 'flex', justifyContent: 'space-between'}}>
                <Button disabled={disableBtn} bsStyle='success' bsSize='large' onClick={this.loginBtnClickHandler}>Войти</Button>
                {regBtn}
            </div>
            <hr />
            {error && <p style={{ color: 'red', fontSize: '20px' }}>{error}</p>}
        </div>;
    }
}

const mapDispatchToProps = (dispatch: ThunkDispatch<{}, {}, any>): IMapDispatchToProps => {
    return ({
        login: (login, pass) => dispatch(loginAction(login, pass))
    });
};

export default connect<{}, IMapDispatchToProps>(null, mapDispatchToProps)(LoginForm);