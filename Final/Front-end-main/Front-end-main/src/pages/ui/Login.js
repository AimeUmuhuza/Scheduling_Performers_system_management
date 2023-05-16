import { Button, Checkbox, Form, Input } from 'antd';
import UserRepository from '../../repository/UserRepository';
import { openNotification } from '../../helpers/OpenNotification';
import { useNavigate } from 'react-router-dom';
import { UserRoute } from '../../helpers/Constants';

const Login = (props) => {
    const navigate = useNavigate();
    const [form] = Form.useForm();
    const [formSignUp] = Form.useForm();
    const onFinish = async (values) => {
        try {
            const res = await UserRepository.login(values);
            if (!res.error) {
                openNotification("Success", res.message, 'success');
                form.resetFields();
                window.localStorage.setItem("userInfo", JSON.stringify(res));
                navigate(UserRoute.dashborad);
            }
        } catch (error) {
            console.log('error:', error.message);
        }
    };
    const onFinishSignUp = async (values) => {
        try {
            const res = await UserRepository.register(values);
            if (!res.error) {
                formSignUp.resetFields();
                openNotification("Success", res.message, 'success');
            }
        } catch (error) {
            console.log('error:', error.message);
        }
    };
    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };
    return (<div>
        <h4 style={{fontSize:'30px',left:'35%',top:'5%',textAlign:'center',position:'fixed'}}>Welcome to SPMS Authentication Page</h4>
        <div style={{display:'flex',justifyContent:'space-around',padding:'10%',backgroundColor:'#CBD0D5',height:'100vh'}}>
            <div style={{borderRight:'1px solid #3E4A5B',padding:'20px'}}>
                
                <Form
                    name="loginform"
                    form={form}
                    labelCol={{
                        span: 8,
                    }}
                    wrapperCol={{
                        span: 16,
                    }}
                    style={{
                        maxWidth: 600,
                    }}
                    initialValues={{
                        remember: true,
                    }}
                    onFinish={onFinish}
                    onFinishFailed={onFinishFailed}
                    autoComplete="off"
                >
                    <Form.Item
                        label="Username"
                        name="username"
                        rules={[
                            {
                                required: true,
                                message: 'Please input your username!',
                            },
                        ]}
                    >
                        <Input />
                    </Form.Item>

                    <Form.Item
                        label="Password"
                        name="password"
                        rules={[
                            {
                                required: true,
                                message: 'Please input your password!',
                            },
                        ]}
                    >
                        <Input.Password />
                    </Form.Item>

                    <Form.Item
                        name="remember"
                        valuePropName="checked"
                        wrapperCol={{
                            offset: 8,
                            span: 16,
                        }}
                    >
                        <Checkbox>Remember me</Checkbox>
                    </Form.Item>

                    <Form.Item
                        wrapperCol={{
                            offset: 8,
                            span: 16,
                        }}
                    >
                        <Button type="primary" htmlType="submit">
                            Login
                        </Button>
                    </Form.Item>
                </Form>
            </div>
            <div style={{borderLeft:'1px solid #3E4A5B',padding:'20px'}}>
                <Form
                    name="signupform"
                    form={formSignUp}
                    labelCol={{
                        span: 8,
                    }}
                    wrapperCol={{
                        span: 16,
                    }}
                    style={{
                        maxWidth: 600,
                    }}
                    initialValues={{
                        remember: true,
                    }}
                    onFinish={onFinishSignUp}
                    onFinishFailed={onFinishFailed}
                    autoComplete="off"
                >
                    <Form.Item
                        label="First Name"
                        name="firstName"
                        rules={[
                            {
                                required: true,
                                message: 'Please input your first Name!',
                            },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        label="Last Name"
                        name="lastName"
                        rules={[
                            {
                                required: true,
                                message: 'Please input your last Name!',
                            },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        label="E-mail"
                        name="email"
                        rules={[
                            {
                                required: true,
                                message: 'Please input your E-mail!',
                            },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        label="Username"
                        name="username"
                        rules={[
                            {
                                required: true,
                                message: 'Please input your username!',
                            },
                        ]}
                    >
                        <Input />
                    </Form.Item>

                    <Form.Item
                        label="Password"
                        name="password"
                        rules={[
                            {
                                required: true,
                                message: 'Please input your password!',
                            },
                        ]}
                    >
                        <Input.Password />
                    </Form.Item>

                    <Form.Item
                        wrapperCol={{
                            offset: 8,
                            span: 16,
                        }}
                    >
                        <Button type="primary" htmlType="submit">
                            Register
                        </Button>
                    </Form.Item>
                </Form>
            </div>

        </div>
    </div>
    )
};
export default Login;