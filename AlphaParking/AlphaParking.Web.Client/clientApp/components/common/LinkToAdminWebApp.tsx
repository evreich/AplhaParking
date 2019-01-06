import * as React from 'react';

import * as Consts from '../../constants/common';

interface IProps {
    text: string;
}

const LinkToAdminWebApp: React.SFC<IProps> = (props) => {
    const { text } = props;

    const clickHandler = (event: React.MouseEvent<HTMLAnchorElement>) => {
        event.preventDefault();
        window.open(Consts.ADMIN_WEB_APP_URL, '_blank');
    };

    return <a onClick={clickHandler}>{text}</a>;
};

export default LinkToAdminWebApp;