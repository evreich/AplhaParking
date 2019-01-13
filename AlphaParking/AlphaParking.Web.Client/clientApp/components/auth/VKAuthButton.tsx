import * as React from 'react';

import { Button, ButtonToolbar } from 'react-bootstrap';

import * as Consts from '../../constants/vkAuth';

const VKAuthButton: React.SFC<{ text: string; }> = (props) => {
    const url = Consts.OAUTH_VK_AUTH;

    const vkAuthHandler = (event: React.MouseEvent<Button>): void => {
        if (typeof window !== 'undefined')
            window.location.href = url;
    };

    return <ButtonToolbar>
        <Button bsStyle='primary' bsSize='large' onClick={vkAuthHandler}>
            {props.text}
        </Button>
    </ButtonToolbar>;
};

export default VKAuthButton;