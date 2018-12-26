import * as React from 'react';

import { Button, ButtonToolbar } from 'react-bootstrap';

import consts from '../../constants';

const VKAuthButton: React.SFC = () => {
    const queryParams = `?client_id=${consts.VK_CLIENT_ID}&display=page&redirect_uri=http://127.0.0.1:8383/vk/auth&scope=email&response_type=code&v=5.92`;
    const url = `${consts.OAUTH_VK_AUTH}${queryParams}`;

    const vkAuthHandler = (event: React.MouseEvent<Button>): void => {
        if (typeof window !== 'undefined')
            window.location.href = url;
    };

    return <ButtonToolbar>
        <Button bsStyle='primary' bsSize='large' onClick={vkAuthHandler}>
            Регистрация VK
        </Button>
    </ButtonToolbar>;
};

export default VKAuthButton;