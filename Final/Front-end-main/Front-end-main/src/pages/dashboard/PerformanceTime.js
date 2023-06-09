import { Space, Table } from 'antd';
import EventRepository from '../../repository/EventRepository';
import { useEffect, useState } from 'react';
import { DeleteOutlined } from '@ant-design/icons';
import { openNotification } from '../../helpers/OpenNotification';
const PerformanceTime = ({eventId}) => {
    const [performanceTime,setPerformanceTime] = useState([]);
    const initializePerformanceTime = async () => {
        try {
            const data = await EventRepository.getPerformanceTime(eventId,0, 10);
            if (!data.error) {
                setPerformanceTime([...data.data]);
            }
        } catch (error) {
            console.log(error);
        }
    }
    const handleDelete = async (id) => {
        try {
            const res = await EventRepository.deletePerformanceTime(id);
            if (!res.error) {
                openNotification("Success Message ", "Performance Time Deleted", 'success');
                initializePerformanceTime();
            }
        } catch (error) {
            console.log(error);
        }
    }
    const columns = [
        {
            title: 'Artist',
            key: 'artist',
            render: (_, { artist }) => (<>{artist.firstName+" "+artist.lastName}</>)
        },
        {
            title: 'Start Time',
            dataIndex:'startDate',
            key: 'startDate',
        },
        {
            title: 'End Date',
            dataIndex:'endDate',
            key: 'endDate',
        },
        {
            title: 'Performed',
            dataIndex:'performed',
            key: 'performed',
            render: (_,record)=>(<div>{record.performed?"YES":"NO"}</div>)
        },
        {
            title: 'Action',
            key: 'action',
            render: (_, record) => (
                <Space size="middle">
                    <div><DeleteOutlined onClick={() => handleDelete(record.id)} /></div>
                </Space>
            ),
        }
    ]
    useEffect(()=>{
        initializePerformanceTime();
    })
    return (
        <div>
            <Table columns={columns} dataSource={performanceTime.map(ev => ({ key: ev.id, ...ev }))} />
        </div>
    );
};

export default PerformanceTime;

// import { Checkbox, DatePicker, Form, Input, InputNumber, Popconfirm, Select, Table, Typography } from 'antd';
// import EventRepository from '../../repository/EventRepository';
// import { useEffect, useState } from 'react';
// const EditableCell = ({
//     editing,
//     dataIndex,
//     title,
//     inputType,
//     record,
//     index,
//     children,
//     ...restProps
// }) => {
//     const inputNode = inputType === 'date' ? <DatePicker showTime /> :inputType === 'checkBox'?<Checkbox />:(inputType=='select')?<Select />:<Input />;
//     return (
//         <td {...restProps}>
//             {editing ? (
//                 <Form.Item
//                     name={dataIndex}
//                     style={{
//                         margin: 0,
//                     }}
//                     rules={[
//                         {
//                             required: true,
//                             message: `Please Input ${title}!`,
//                         },
//                     ]}
//                 >
//                     {inputNode}
//                 </Form.Item>
//             ) : (
//                 children
//             )}
//         </td>
//     );
// };
// const App = ({ eventId }) => {
//     const [form] = Form.useForm();
//     const [performanceTime, setPerformanceTime] = useState([]);
//     const initializePerformanceTime = async (eventId) => {
//         try {
//             const data = await EventRepository.getPerformanceTime(eventId, 0, 10);
//             if (!data.error) {
//                 setPerformanceTime([...data.data]);
//             }
//         } catch (error) {
//             console.log(error);
//         }
//     }
//     const [data, setData] = useState(performanceTime.map(pf=>({key:pf.id,...pf})));
//     const [editingKey, setEditingKey] = useState('');
//     const isEditing = (record) => record.key === editingKey;
//     const edit = (record) => {
//         form.setFieldsValue({
//             ...record,
//         });
//         setEditingKey(record.key);
//     };
//     const cancel = () => {
//         setEditingKey('');
//     };
//     const save = async (key) => {
//         try {
//             const row = await form.validateFields();
//             const newData = [...data];
//             const index = newData.findIndex((item) => key === item.key);
//             if (index > -1) {
//                 const item = newData[index];
//                 newData.splice(index, 1, {
//                     ...item,
//                     ...row,
//                 });
//                 setData(newData);
//                 setEditingKey('');
//             } else {
//                 newData.push(row);
//                 setData(newData);
//                 setEditingKey('');
//             }
//         } catch (errInfo) {
//             console.log('Validate Failed:', errInfo);
//         }
//     };
//     const columns = [
//         {
//             title: 'Artist',
//             key: 'artist',
//             dataIndex: 'artist',
//             render: (_, { artist }) => (<>{artist.firstName + " " + artist.lastName}</>)
//         },
//         {
//             title: 'Start Time',
//             dataIndex: 'startDate',
//             key: 'startDate',
//         },
//         {
//             title: 'End Date',
//             dataIndex: 'endDate',
//             key: 'endDate',
//         },
//         {
//             title: 'Performed',
//             dataIndex: 'performed',
//             key: 'performed',
//             render: (_, record) => (<div>{record.performed ? "YES" : "NO"}</div>)
//         },
//         {
//             title: 'operation',
//             dataIndex: 'operation',
//             render: (_, record) => {
//                 const editable = isEditing(record);
//                 return editable ? (
//                     <span>
//                         <Typography.Link
//                             onClick={() => save(record.key)}
//                             style={{
//                                 marginRight: 8,
//                             }}
//                         >
//                             Save
//                         </Typography.Link>
//                         <Popconfirm title="Sure to cancel?" onConfirm={cancel}>
//                             <a>Cancel</a>
//                         </Popconfirm>
//                     </span>
//                 ) : (
//                     <Typography.Link disabled={editingKey !== ''} onClick={() => edit(record)}>
//                         Edit
//                     </Typography.Link>
//                 );
//             },
//         },
//     ];
//     const mergedColumns = columns.map((col) => {
//         if (!col.editable) {
//             return col;
//         }
//         return {
//             ...col,
//             onCell: (record) => ({
//                 record,
//                 inputType: ['startDate','endDate'].includes(col.dataIndex) ? 'date' : (col.dataIndex=='performed')?'checkBox':(col.dataIndex=='artist')?'select':'text',
//                 dataIndex: col.dataIndex,
//                 title: col.title,
//                 editing: isEditing(record),
//             }),
//         };
//     });

//     useEffect(() => {
//         initializePerformanceTime(eventId);
//         setData(performanceTime.map(pf=>({key:pf.id,...pf})));
//     })
//     return (
//         <Form form={form} component={false}>
//             <Table
//                 components={{
//                     body: {
//                         cell: EditableCell,
//                     },
//                 }}
//                 bordered
//                 dataSource={data}
//                 columns={mergedColumns}
//                 rowClassName="editable-row"
//                 pagination={{
//                     onChange: cancel,
//                 }}
//             />
//         </Form>
//     );
// };
// export default App;