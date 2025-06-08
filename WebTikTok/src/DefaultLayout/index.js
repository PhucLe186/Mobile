import classNames from 'classnames/bind';
import styles from './default.module.scss';

import SlideBar from '~/layout/SlideBar';

const cx = classNames.bind(styles);
function defaultLayout({ children }) {
    return (
        <div className={cx('parent')}>
            <SlideBar />
            <div>{children}</div>
        </div>
    );
}

export default defaultLayout;
