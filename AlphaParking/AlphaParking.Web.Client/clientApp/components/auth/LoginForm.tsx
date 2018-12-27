import * as React from 'react';
import { Button, ControlLabel, FormControl, FormGroup, HelpBlock } from 'react-bootstrap';
import { RouteComponentProps, withRouter } from 'react-router-dom';

import consts from '../../constants';

interface IFieldGroupProps {
    id: string;
    type: string;
    placeholder?: string;
    label: string;
    help?: string;
    value: any;
    onChange: (event: React.FormEvent<FormControl>) => void;
}

const FieldGroup: React.SFC<IFieldGroupProps> = ({ id, label, help, ...props }) => {
    return (
        <FormGroup controlId={id}>
            <ControlLabel>{label}</ControlLabel>
            <FormControl {...props} />
            {help && <HelpBlock>{help}</HelpBlock>}
        </FormGroup>
    );
};

interface ILoginFormState {
    login: string;
    password: string;
    error: string;
}

// tslint:disable-next-line:no-empty-interface
interface ILoginFormProps extends RouteComponentProps {
}

class LoginForm extends React.Component<ILoginFormProps, ILoginFormState> {
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
        const conf: RequestInit = {
            body: JSON.stringify({
                login,
                pass
            }),
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/json; charset=UTF-8'
            },
            method: 'POST',
            mode: 'cors'
        };
        if (error) return;

        fetch(`${consts.SERVER_API}/login`, conf)
            .then((response) => {
                if (response.ok)
                    return response.json();
                else
                    response.json()
                        .then((err) => this.setState({
                            error: err.error
                        }
                        ));
            })
            .then((payload: { access_token: string }) =>
                localStorage.setItem('access_token', payload.access_token))
            .then(() => this.props.history.push('/hello'))
            .catch((err) => this.setState({
                error: err.message
            }));
    }

    render() {
        const { login, password, error } = this.state;
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
            <Button bsStyle='success' bsSize='large' onClick={this.loginBtnClickHandler}>Войти</Button>
            {error && <div style={{ color: 'red' }}>{error}</div>}
        </div>;
    }
}

export default withRouter(LoginForm);