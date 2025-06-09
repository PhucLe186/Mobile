import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSearch } from '@fortawesome/free-solid-svg-icons';

import { FaUserGroup } from 'react-icons/fa6';
import { AiFillHome } from 'react-icons/ai';
import { RiUserReceived2Line } from 'react-icons/ri';
import { FaRegCompass } from 'react-icons/fa';
import { BiMessageAltMinus } from 'react-icons/bi';
import { LuSend } from 'react-icons/lu';
import { MdOutlineAddBox } from 'react-icons/md';
import classNames from 'classnames/bind';
import { useState } from 'react';
import Menu from './menu/menu';
import MenuList from './menu/MenuList';
import styles from './slidebar.module.scss';
import routesconfig from '~/config/routes';
import logo from '~/asset/img/logo.png';
import Activity from '~/AppComponent/Page/Activity';

const cx = classNames.bind(styles);

function SlideBar() {
    const [Open, setOpen] = useState(false);
    const [activeCustomMenu, setActiveCustomMenu] = useState(null);
    const [isShrink, setIsShrink] = useState(false);

    const handleClick1 = () => {
        setOpen(!Open);
        setActiveCustomMenu('activity');
        setIsShrink(true);
    };
    const handleClick2 = () => {
        setOpen(false);

        setActiveCustomMenu(null);
    };

    return (
        <div className={cx('parent')}>
            <div className={cx('SlideBar', { Open: Open })}>
                <div className={cx('inner')}>
                    <div className={cx('logo_item')}>
                        <img className={cx('logo')} src={logo} alt="tiktok" />
                        {Open === true ? (
                            ''
                        ) : (
                            <strong className={cx('text')}>
                                TikT<span className={cx('text_o')}></span>k
                            </strong>
                        )}
                    </div>
                    <div className={cx('search_wrapper', { Open: Open })}>
                        <FontAwesomeIcon icon={faSearch} className={cx('search_icon')} />
                        <input className={cx('search')} type="text" placeholder="Tìm kiếm" />
                    </div>
                </div>
                <Menu>
                    <MenuList
                        isShrink={isShrink}
                        click={handleClick2}
                        isActive={activeCustomMenu === null ? null : false}
                        to={routesconfig.home}
                        icon={<AiFillHome style={{ fontSize: '32px' }} />}
                        title={'Đề xuất'}
                    />
                    <MenuList
                        isShrink={isShrink}
                        click={handleClick2}
                        isActive={activeCustomMenu === null ? null : false}
                        to={routesconfig.explore}
                        icon={<FaRegCompass style={{ fontSize: '32px' }} />}
                        title={'Khám phá'}
                    />
                    <MenuList
                        isShrink={isShrink}
                        click={handleClick2}
                        isActive={activeCustomMenu === null ? null : false}
                        to={routesconfig.following}
                        icon={<RiUserReceived2Line style={{ fontSize: '32px' }} />}
                        title={'Đã follow'}
                    />
                    <MenuList
                        isShrink={isShrink}
                        click={handleClick2}
                        isActive={activeCustomMenu === null ? null : false}
                        to={routesconfig.friend}
                        icon={<FaUserGroup style={{ fontSize: '32px' }} />}
                        title={'Bạn bè'}
                    />

                    <MenuList
                        isShrink={isShrink}
                        click={handleClick2}
                        isActive={activeCustomMenu === null ? null : false}
                        to={routesconfig.Upload}
                        icon={<MdOutlineAddBox style={{ fontSize: '32px' }} />}
                        title={'Tải lên'}
                    />
                    <MenuList
                        isShrink={isShrink}
                        click={handleClick1}
                        isActive={activeCustomMenu}
                        icon={<BiMessageAltMinus style={{ fontSize: '32px' }} />}
                        title={'Hoạt động'}
                    />
                    <MenuList
                        isShrink={isShrink}
                        click={handleClick2}
                        isActive={activeCustomMenu === null ? null : false}
                        to={routesconfig.message}
                        icon={<LuSend style={{ fontSize: '32px' }} />}
                        title={'Tin nhắn'}
                    />
                </Menu>
            </div>
            {Open && <Activity />}
        </div>
    );
}

export default SlideBar;
