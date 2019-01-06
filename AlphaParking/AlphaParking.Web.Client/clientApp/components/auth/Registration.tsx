import * as React from 'react';

import RegistrationForm from './forms/RegistrationForm';
import VKAuthButton from './VKAuthButton';

export default class Registration extends React.Component {
    render() {
        return <div>
            <h2>Форма регистрации</h2>
            <RegistrationForm />
            <hr />
            <VKAuthButton />
        </div>;
    }
}