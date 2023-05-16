import React, { Component, useEffect, useState } from 'react';
import AuthLayout from '../../components/common/AuthLayout';
import EventRepository from '../../repository/EventRepository';
import { Button, DatePicker, Form, Input, Modal, Pagination, Select, Space, Table } from 'antd';
import { DeleteOutlined, EditOutlined, SubnodeOutlined } from '@ant-design/icons';
import { useForm } from 'antd/es/form/Form';
import { openNotification } from '../../helpers/OpenNotification';
import PerformanceTime from './PerformanceTime';
import dayjs from 'dayjs';
import customParseFormat from 'dayjs/plugin/customParseFormat';
dayjs.extend(customParseFormat);


const NewPerformanceModal = ({ record, initalizeEvent }) => {
    const [showPerformanceModal, setPerformanceModal] = useState(false);
    const [form] = useForm();
    const [artists, setArtists] = useState([]);
    const initalizeArtists = async () => {
        try {
            const data = await EventRepository.getArtists(0, 10);
            if (!data.error) {
                setArtists([...data.data]);
            }
        } catch (error) {
            console.log(error);
        }
    }
    const onFinish = async (values) => {
        try {
            const artist = values.artist;
            delete values.artist;
            const resp = await EventRepository.createEventPerformance(artist, record.id, values);
            if (!resp.error) {
                setPerformanceModal(false);
                return initalizeEvent();
            }
        } catch (error) {
            console.log(error);
        }
    };
    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    useEffect(() => {
        initalizeArtists();
    }, []);
    return (
        <div>
            <Button onClick={() => setPerformanceModal(true)}>Add new</Button>
            <Modal title="Performance Time Form" open={showPerformanceModal} footer={[]} onCancel={() => setPerformanceModal(false)}>
                <Form
                    form={form}
                    name="performanceTime"
                    labelCol={{
                        span: 4,
                    }}
                    wrapperCol={{
                        span: 16,
                    }}
                    style={{
                        maxWidth: 600,
                    }}
                    onFinish={onFinish}
                    onFinishFailed={onFinishFailed}
                    autoComplete="off"
                >
                    <Form.Item
                        label="Select Artist"
                        name="artist"
                        rules={[
                            {
                                required: true,
                                message: 'Artist is Required',
                            },
                        ]}
                    >
                        <Select showSearch options={artists.map(a => ({ value: a.id, label: a.firstName + " " + a.lastName }))} />
                    </Form.Item>
                    <Form.Item
                        label="Start Date"
                        name="startDate"
                        rules={[
                            {
                                required: true,
                                message: 'Event Start Date is required',
                            },
                        ]}
                    >
                        <DatePicker showTime={true} style={{ width: '100%' }} format="YYYY-MM-DD HH:mm:ss" />
                    </Form.Item>

                    <Form.Item
                        label="End Date"
                        name="endDate"
                        rules={[
                            {
                                required: true,
                                message: 'Event End Date is required',
                            },
                        ]}
                    >
                        <DatePicker showTime={true} style={{ width: '100%' }} format="YYYY-MM-DD HH:mm:ss" />
                    </Form.Item>

                    <Form.Item >
                        <Button type="primary" htmlType="submit">
                            Submit
                        </Button>
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    )
}


const Events = (props) => {
    const [events, setEvents] = useState({});
    const [page, setPage] = useState(0);

    const initalizeEvent = async (currentPage) => {
        try {
            console.log(currentPage);
            currentPage=currentPage-1;
            setPage(currentPage);
            const data = await EventRepository.events(currentPage, 2);
            if (!data.error) {
                setEvents({ ...data.data });
            }
        } catch (error) {
            console.log(error);
        }
    }
    const handleDelete = async (eventid) => {
        try {
            const data = await EventRepository.deleteEvent(eventid);
            if (!data.error) {
                initalizeEvent();
            }
        } catch (error) {
            console.log(error);
        }
    }
    const columns = [
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'Location',
            dataIndex: 'location',
            key: 'location',
        },
        {
            title: 'Date',
            dataIndex: 'date',
            key: 'date',
        },
        {
            title: 'Start Date',
            dataIndex: 'startDate',
            key: 'startDate',
        },
        {
            title: 'End Date',
            dataIndex: 'endDate',
            key: 'endDate',
        },
        {
            title: 'Action',
            key: 'action',
            render: (_, record) => (
                <Space size="middle">
                    <div onClick={() => showModalEdit(record)}><EditOutlined /></div>
                    <div><DeleteOutlined onClick={() => handleDelete(record.id)} /></div>
                </Space>
            ),
        },
    ];

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [form] = useForm();
    const showModal = () => {
        form.setFieldsValue({ name: '', location: '', date: null, startDate: null, endDate: null });
        setSelectedId(null);
        setIsModalOpen(true);
    };
    const [selectedId, setSelectedId] = useState(null);
    const showModalEdit = (data) => {
        setSelectedId(data.id);
        form.setFieldsValue({ name: data.name, location: data.location, date: dayjs(data.date), startDate: dayjs(data.startDate), endDate: dayjs(data.endDate) });
        setIsModalOpen(true);
    };

    const handleOk = () => {
        setIsModalOpen(false);
    };
    const handleCancel = () => {
        setIsModalOpen(false);
    };

    const onFinish = async (values) => {
        let res = null;
        if (selectedId != null) {
            values.id = selectedId;
            res = await EventRepository.updateEvent(values);
        } else {
            res = await EventRepository.createEvent(values);
        }

        if (!res.error) {
            openNotification("Success Message ", "Event Saved Success", 'success');
            initalizeEvent();
            form.resetFields();
        }
    };
    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    useEffect(() => {
        initalizeEvent(1);
    }, [])

    return (
        <AuthLayout>
            <div>
                <Space align='right'>
                    <Button onClick={showModal}>Add new</Button>
                    <Modal title="Event Form" open={isModalOpen} footer={[]} onOk={handleOk} onCancel={handleCancel}>
                        <Form
                            form={form}
                            name="event"
                            labelCol={{
                                span: 4,
                            }}
                            wrapperCol={{
                                span: 16,
                            }}
                            style={{
                                maxWidth: 600,
                            }}
                            onFinish={onFinish}
                            onFinishFailed={onFinishFailed}
                            autoComplete="off"
                        >
                            <Form.Item
                                label="Name"
                                name="name"
                                rules={[
                                    {
                                        required: true,
                                        message: 'Event Name is Required',
                                    },
                                ]}
                            >
                                <Input />
                            </Form.Item>
                            <Form.Item
                                label="Location"
                                name="location"
                                rules={[
                                    {
                                        required: true,
                                        message: 'Event Location is Required',
                                    },
                                ]}
                            >
                                <Input />
                            </Form.Item>

                            <Form.Item
                                label="Date"
                                name="date"
                                rules={[
                                    {
                                        required: true,
                                        message: 'Event Date is required',
                                    },
                                ]}
                            >
                                <DatePicker style={{ width: '100%' }} />
                            </Form.Item>

                            <Form.Item
                                label="Start Date"
                                name="startDate"
                                rules={[
                                    {
                                        required: true,
                                        message: 'Event Start Date is required',
                                    },
                                ]}
                            >
                                <DatePicker showTime={true} style={{ width: '100%' }} format="YYYY-MM-DD HH:mm:ss" />
                            </Form.Item>

                            <Form.Item
                                label="End Date"
                                name="endDate"
                                rules={[
                                    {
                                        required: true,
                                        message: 'Event End Date is required',
                                    },
                                ]}
                            >
                                <DatePicker showTime={true} style={{ width: '100%' }} format="YYYY-MM-DD HH:mm:ss" />
                            </Form.Item>

                            <Form.Item
                            >
                                <Button type="primary" htmlType="submit">
                                    Submit
                                </Button>
                            </Form.Item>
                        </Form>
                    </Modal>
                </Space>
                <Table pagination={false} expandable={{
                    expandedRowRender: (record) => (
                        <div>
                            <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                                <h4>Performance Time</h4>
                                <NewPerformanceModal record={record} />
                            </div>
                            <PerformanceTime initalizeEvent={initalizeEvent} eventId={record.id} />
                        </div>
                    ),
                    rowExpandable: (record) => record.name !== 'Not Expandable',
                }} columns={columns} dataSource={events.content?.map(ev => ({ key: ev.id, ...ev }))} />
                <Pagination onChange={(p) => initalizeEvent(p)} defaultPageSize={2} current={page+1} defaultCurrent={1}
                    total={events.totalElements||0} pageSize={events.totalPages||2} size="small" />
            </div>
        </AuthLayout>
    );
}

export default Events;