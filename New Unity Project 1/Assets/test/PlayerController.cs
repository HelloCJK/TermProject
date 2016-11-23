﻿using UnityEngine;
using System.Collections;

[System.Serializable]
public class Boundary{
    public float xMin, xMax, zMin, zMax;
}

public class PlayerController : MonoBehaviour {

    Rigidbody rb;
    public float speed;
    public float tilt;
    public Boundary boundary = new Boundary();

    public GameObject shot;
    public Transform shotSpawn;
    public GameObject asteroid;

    public int life;

    public float fireRate;
    private float nextFire;

    AudioSource fireAudio;

    void Start()
    {
        rb = GetComponent<Rigidbody>();
        fireAudio = GetComponent<AudioSource>();
    }

    void Update()
    {
        if (Input.GetKey(KeyCode.Mouse0) && Time.time > nextFire)
        {
            Instantiate(shot, shotSpawn.position, shotSpawn.rotation);
            nextFire = Time.time + fireRate;
            fireAudio.Play();
        }
    }

    void FixedUpdate()
    {
        float moveHorizontal = Input.GetAxis("Mouse X");
        float moveVertical = Input.GetAxis("Mouse Y");

        rb.velocity = new Vector3(moveHorizontal, 0,moveVertical) * speed;
        rb.position = new Vector3(
            Mathf.Clamp(rb.position.x, boundary.xMin, boundary.xMax)
            , 0
            , Mathf.Clamp(rb.position.z, boundary.zMin, boundary.zMax)
        );

        rb.rotation = Quaternion.Euler(0,0, (-1) * rb.velocity.x * tilt);
   }
}
