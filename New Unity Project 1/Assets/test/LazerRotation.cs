using UnityEngine;
using System.Collections;

public class LazerRotation : MonoBehaviour {

    Rigidbody rb;
    public float tumble;
    public float speed;

    // Use this for initialization
    void Start () {

        rb = GetComponent<Rigidbody>();
        //rb.angularVelocity.z = Random.insideUnitSphere* tumble;
        //rb.rotation = Quaternion.Euler(0, 0, (-1) * tumble);
        //rb.angularVelocity = new Vector3(0, 1, 0) * tumble;
    }
	
	// Update is called once per frame
	void Update ()
    {
        transform.Rotate(new Vector3(0, 0, 1) * tumble);
    }
}
