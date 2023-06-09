import React from 'react';
import {
    FileOutlined,
    LogoutOutlined,
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    UploadOutlined,
    UserOutlined,
    VideoCameraOutlined,
} from '@ant-design/icons';
import { Breadcrumb, Button, Layout, Menu, theme } from 'antd';
import { useLocation, useNavigate } from "react-router-dom";
import { useState } from 'react';
import { UserRoute, getUserData } from '../../helpers/Constants';
const { Header, Sider, Content } = Layout;

const AuthLayout = (props) => {
    const [collapsed, setCollapsed] = useState(false);
    const { token: { colorBgContainer }, } = theme.useToken();
    const navigate = useNavigate();
    const location = useLocation();
    const handleLogout =  ()=>{
        window.localStorage.removeItem('userInfo');
        navigate('/login');
    }
    return (
        <Layout>
            <Sider trigger={null} collapsible collapsed={collapsed}>
                <div className="logo" />
                <Menu
                    theme="dark"
                    mode="inline"
                    defaultSelectedKeys={[location.pathname]}
                    triggerSubMenuAction='click'
                    onClick={e=>navigate(e.key)}
                    items={[
                        {
                            icon: <UserOutlined />,
                            label: 'Dashboard',
                            key: UserRoute.dashborad
                        },
                        {
                            icon: <VideoCameraOutlined />,
                            label: 'Events',
                            key: UserRoute.events
                        },
                        {
                            icon: <UploadOutlined />,
                            label: 'Artists',
                            key: UserRoute.artists
                        },
                        {
                            icon: <FileOutlined />,
                            label: 'Contracts',
                            key: UserRoute.contacts
                        },
                    ]}
                />
            </Sider>
            <Layout>
                <Header
                    style={{
                        padding: 0,
                        background: colorBgContainer,
                        display:'flex',
                        justifyContent:'space-between'
                    }}
                >
                    <Button
                        type="text"
                        icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
                        onClick={() => setCollapsed(!collapsed)}
                        style={{
                            fontSize: '16px',
                            width: 64,
                            height: 64,
                        }}
                    />
                    <div style={{display:'flex'}}>
                        <h2 style={{color:'#3E4A5B'}}>Welcome, {getUserData()?.firstName+' '+getUserData()?.lastName}</h2>
                    <Button
                        type="text"
                        icon={<LogoutOutlined />}
                        onClick={() => handleLogout()}
                        style={{
                            fontSize: '16px',
                            width: 64,
                            height: 64,
                        }}
                    />
                    </div>
                </Header>
                <Breadcrumb style={{ margin: '8px 16px 0' }}>
                    <Breadcrumb.Item>Dashboard</Breadcrumb.Item>
                    <Breadcrumb.Item>List</Breadcrumb.Item>
                    <Breadcrumb.Item>App</Breadcrumb.Item>
                </Breadcrumb>
                <Content
                    style={{
                        margin: '24px 16px',
                        padding: 24,
                        minHeight: '100vh',
                        background: colorBgContainer,
                    }}
                >
                    {props.children}
                </Content>
            </Layout>
        </Layout >
    );
}

export default AuthLayout;